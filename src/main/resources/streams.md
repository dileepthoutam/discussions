
### java streams

below is about Optional<T>
.get() either throws exception or returns the value
.ifPresent(Consumer c) Does nothing if value is absent or calls Consumer with value
.isPresent()    true or false
orElse(T other) returns other param or value
orElseGet(Supplier s) returns result of calling Supplier or returns the value
orElseThrow()   it throws NoSuchElementException    returns value
orElseThrow(Supplier s) throws exception created by calling Supplier or returns value

in java a stream is sequence of data

there are three parts of a stream

source, intermediate operations and terminal operations



