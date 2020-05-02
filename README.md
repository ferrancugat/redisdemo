## Data structures
sortedsets, lists and hashes.

## Commands
The command definitions are all available at http://redis.io/commands.
1. SET key value
``
Set key to hold the string value. If key already holds a value, it is overwritten, regardless of its type. Any
 previous time to live associated with the key is discarded on successful SET operation. Simple string reply: OK if SET was executed correctly. Null reply: a Null Bulk Reply is returned if the SET operation was not performed because the user specified the NX or XX option but the condition was not met.
``
2. SET key value EX seconds
``
Set the specified expire time, in seconds.
``

3. GET key
``
Bulk string reply: the value of key, or nil when key does not exist.
``
4. DEL key
``
Removes the specified keys. A key is ignored if it does not exist.
``
5. DBSIZE
``
Return the number of keys in the currently-selected database.
``
6. INCR key
``
Increments the number stored at key by one. If the key does not exist, it is set to 0 before performing the operation. An error is returned if the key contains a value of the wrong type or contains a string that can not be represented as integer. This operation is limited to 64 bit signed integers.
``
7. ZADD key score member
``
Adds all the specified members with the specified scores to the sorted set stored at key. It is possible to specify multiple score / member pairs. If a specified member is already a member of the sorted set, the score is updated and the element reinserted at the right position to ensure the correct ordering.
``

8. ZCARD key
``
Returns the sorted set cardinality (number of elements) of the sorted set stored at key.
``
9. ZRANK key member
``
Returns the rank of member in the sorted set stored at key, with the scores ordered from low to high. The rank (or index) is 0-based, which means that the member with the lowest score has rank 0.
``
10. ZRANGE key start stop
``
Returns the specified range of elements in the sorted set stored at key. The elements are considered to be ordered from the lowest to the highest score. Lexicographical order is used for elements with equal score.
``

## Atomicity
One of the key benefits of Redis is that it guarantees atomic, ordered access to data. 
