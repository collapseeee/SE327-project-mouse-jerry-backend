---
name: spring-boot-layered-architecture
description: >-
  Enforce and implement the standard 5-tier layered enterprise architecture for Spring Boot 
  (Controller -> Service -> DAO -> Repository -> Database) using MapStruct DTOs, 
  Lombok, and JWT-based Spring Security.
---

# Spring Boot 5-Tier Layered Architecture Guide

When implementing or extending features in this Spring Boot project, you must follow the strict 5-tier layered architecture and conventions outlined below.

---

## 1. Architectural Overview & Request Flow

Every incoming HTTP request must traverse these layers in strict descending order. Never skip layers (e.g., controllers must never call repositories or DAOs directly):

```
[HTTP Request]
       │
       ▼
1. Controller Layer       (@RestController)
   └── Exposes endpoints, validates HTTP requests, handles pagination headers.
   └── Maps Entities to DTOs via MapStruct before returning responses.
       │
       ▼
2. Service Layer          (Interface + @Service Impl)
   └── Implements business logic, orchestrates DAOs, manages @Transactional boundaries.
       │
       ▼
3. DAO Layer              (Interface + @Repository Impl with @Profile("db"))
   └── Abstraction over persistence strategies (allows switching between DB, mock, or cache).
       │
       ▼
4. Repository Layer       (Spring Data JpaRepository)
   └── Database operations, JPQL/native queries, pagination queries.
       │
       ▼
5. Database / Entity      (JPA @Entity + PostgreSQL)
```

---

## 2. Core Conventions & Guidelines

1. **Dependency Injection:**
   * Always prefer constructor injection using Lombok's `@RequiredArgsConstructor`.
   * Declare injected dependencies as `final` or private `final`.
   * Avoid `@Autowired` on class fields.
2. **DTO & Serialization Separation:**
   * **Never return raw JPA `@Entity` classes directly from controllers.**
   * Always map entities to dedicated DTOs (e.g., `EntityDTO`) using a centralized MapStruct mapper to prevent infinite recursion, stack overflows from bidirectional JPA relationships, and unintentional exposure of internal IDs or passwords.
3. **Transaction Management:**
   * Apply `@Transactional` at the **Service implementation** level for methods performing mutations (`save`, `update`, `delete`, or operations accessing lazy collections).
4. **Interface-Driven Design:**
   * Service and DAO layers must always define an **interface** first, followed by an implementation class (`ServiceImpl`, `DaoDbImpl`).

---

## 3. Layer Implementation Blueprints

### A. Entity Layer (`entity/`)
* Use Lombok (`@Data`, `@Builder`, `@NoArgsConstructor`, `@AllArgsConstructor`).
* Always exclude ID from `@EqualsAndHashCode`: `@EqualsAndHashCode.Exclude`.
* For collections in `@Builder`, initialize them with `@Builder.Default List<...> items = new ArrayList<>();`.
* Handle bidirectional relations carefully (`mappedBy` on the non-owning inverse side).

```java
@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Exclude
    private Long id;

    private String title;

    @ManyToOne
    private Category category;

    @ManyToMany(mappedBy = "items")
    @Builder.Default
    private List<Tag> tags = new ArrayList<>();
}
```

---

### B. Repository Layer (`repository/`)
* Extends `JpaRepository<Entity, ID>`.
* Keep simple CRUD methods implicit; add custom JPQL/derived queries only when necessary.

```java
public interface ItemRepository extends JpaRepository<Item, Long> {
    Page<Item> findByTitleContaining(String title, Pageable pageable);
}
```

---

### C. DAO Layer (`dao/`)
* **Interface (`ItemDao`):** Defines persistence operations needed by business logic.
* **Implementation (`ItemDaoDbImpl`):** Annotated with `@Repository` and `@Profile("db")`.

```java
// Interface
public interface ItemDao {
    Integer getItemCount();
    Page<Item> getItems(Integer pageSize, Integer page);
    Item getItem(Long id);
    Item save(Item item);
}

// Implementation
@Repository
@RequiredArgsConstructor
@Profile("db")
public class ItemDaoDbImpl implements ItemDao {
    private final ItemRepository itemRepository;

    @Override
    public Integer getItemCount() {
        return Math.toIntExact(itemRepository.count());
    }

    @Override
    public Page<Item> getItems(Integer pageSize, Integer page) {
        int pageNumber = (page == null || page < 1) ? 0 : page - 1;
        int size = (pageSize == null || pageSize < 1) ? 10 : pageSize;
        return itemRepository.findAll(PageRequest.of(pageNumber, size));
    }

    @Override
    public Item getItem(Long id) {
        return itemRepository.findById(id).orElse(null);
    }

    @Override
    public Item save(Item item) {
        return itemRepository.save(item);
    }
}
```

---

### D. Service Layer (`service/`)
* **Interface (`ItemService`):** Defines business capabilities.
* **Implementation (`ItemServiceImpl`):** Annotated with `@Service`. Annotate mutations with `@Transactional`.

```java
// Interface
public interface ItemService {
    Page<Item> getItems(Integer pageSize, Integer page);
    Item getItem(Long id);
    Item save(Item item);
}

// Implementation
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemDao itemDao;

    @Override
    public Page<Item> getItems(Integer pageSize, Integer page) {
        return itemDao.getItems(pageSize, page);
    }

    @Override
    public Item getItem(Long id) {
        return itemDao.getItem(id);
    }

    @Override
    @Transactional
    public Item save(Item item) {
        return itemDao.save(item);
    }
}
```

---

### E. DTO & MapStruct Layer (`util/LabMapper.java` & `entity/*DTO.java`)
* MapStruct generates clean, boilerplate-free mapping code at compile-time.
* Avoid circular references in DTOs (e.g., if `ItemDTO` contains `CategoryDTO`, ensure `CategoryDTO` does not recursively contain full `ItemDTO` objects).

```java
@Mapper
public interface LabMapper {
    LabMapper INSTANCE = Mappers.getMapper(LabMapper.class);

    ItemDTO getItemDto(Item item);
    List<ItemDTO> getItemDto(List<Item> items);
}
```

---

### F. Controller Layer (`controller/`)
* Annotated with `@RestController` and `@RequiredArgsConstructor`.
* Returns `ResponseEntity<?>`.
* Attaches pagination count in headers (`x-total-count`).
* Transforms entities to DTOs before responding.

```java
@RestController
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping("/items")
    public ResponseEntity<?> getItems(
            @RequestParam(value = "_limit", required = false, defaultValue = "10") Integer perPage,
            @RequestParam(value = "_page", required = false, defaultValue = "1") Integer page) {
        
        Page<Item> pageOutput = itemService.getItems(perPage, page);
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-total-count", String.valueOf(pageOutput.getTotalElements()));
        
        return ResponseEntity.ok()
                .headers(headers)
                .body(LabMapper.INSTANCE.getItemDto(pageOutput.getContent()));
    }

    @GetMapping("/items/{id}")
    public ResponseEntity<?> getItem(@PathVariable("id") Long id) {
        Item item = itemService.getItem(id);
        if (item == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found");
        }
        return ResponseEntity.ok(LabMapper.INSTANCE.getItemDto(item));
    }
}
```

---

### G. Security / JWT Architecture (`security/`)
* **Stateless Session Management:** `SessionCreationPolicy.STATELESS`.
* **Explicit Authorization Rules:** Always configure `.authorizeHttpRequests(...)` with `.requestMatchers("/api/v1/auth/**", "/error").permitAll().anyRequest().authenticated()`.
* **Token Database Validation:** Verify tokens against the database `Token` entity to support logout/revocation.
* **Service Transactionality:** Keep `authenticate()` and `register()` methods `@Transactional` so associated lazy relations (like linked user roles or organizer profiles) load without `LazyInitializationException`.

---

## 4. Common Pitfalls to Avoid

* **`@Value` Import Conflict:** Always import `org.springframework.beans.factory.annotation.Value`, NEVER `lombok.Value`, when reading properties from YAML files.
* **Null Pagination Parameters:** Always provide default values (`defaultValue = "1"`) or defensively guard against `null` in DAO `PageRequest.of(page - 1, size)` to prevent `NullPointerException`.
* **Disguised 403 Errors:** Always permit `/error` in `SecurityConfiguration` so server-side exceptions (500) don't get swallowed and returned as blank `403 Forbidden` responses.
* **Hibernate Lazy Collections in DTO Mappers:** Ensure service methods converting lazy collections into DTOs run inside an active `@Transactional` boundary.
