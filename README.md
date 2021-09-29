# demo web shop server

## Sorting

overload getAll() method to accept sorting parameter:

```java
    @GetMapping("/paginated-sorted")
    Map<String, Object> getAllPaginatedSorted(
            @RequestParam(required = false) String title,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "title,asc") String[] sort);
```

implement in the class:
```java
	@Override
	public Map<String, Object> getAllPaginatedSorted(String title, int page, int size, String[] sort) {
		return tutorialService.getAll(title, page, size, sort);
	}
```

service interface: overload getAll() for sort parameter
```java
public interface TutorialServiceInterface {
    List<Tutorial> getAll(String title);
    Map<String, Object> getAll(String title, int page, int size); //overloaded method for pagination
    Map<String, Object> getAll(String title, int page, int size, String[] sort); //overloaded method for pagination and sorting
```

Implement in the class:
```java
    //pagination and sorting - overloaded method
    @Override
    public Map<String, Object> getAll(String title, int page, int size, String[] sort) {
        List<Sort.Order> orders = getSortOrders(sort); //object for sorting
        Pageable paging = PageRequest.of(page, size, Sort.by(orders)); // paging with sorting
        return getResponseMap(title, paging);
    }
```

getSortOrders helper method:
```java
    //helper method retrieving a list of sort orders
    private List<Sort.Order> getSortOrders(String[] sort) {
        List<Sort.Order> orders = new ArrayList<>();

        if (sort[0].contains(",")) {
            // will sort more than 2 fields
            // sortOrder="field, direction"
            for (String sortOrder : sort) {
                String[] _sort = sortOrder.split(",");
                orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
            }
        } else {
            // sort=[field, direction]
            orders.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
        }
        return orders;
    }
```

getSortDirection helper method:
```java
    //helper method retrieving the Sort.Direction enum
    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }
        return Sort.Direction.ASC;
    }
```

Now we can send this http request with multiple sort parameters:

http://localhost:5552/api/tutorials/paginated-sorted?page=0&size=2&sort=title,desc&sort=description,desc

Debug this to see step by step what is going on.

Deploy and test on Heroku as well.