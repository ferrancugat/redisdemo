package org.demo.nosql.redisdemo.collections;

import java.util.AbstractMap.SimpleEntry;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 *
 */
public class ScoringSortedSet implements SortedSet<Map.Entry<Double, String>> {

    private final Map<String, Double> userScoresMap = new HashMap<>();

    private final Comparator<Map.Entry<Double, String>> comparatorNaturalOrderScoreAndKey = (o1, o2) -> {
        int key = o1.getKey()
                .compareTo(o2.getKey());   // Natural order from low to high
        if (key != 0) {
            return key;
        }
        return o1.getValue()
                .compareTo(o2.getValue());   //Lexicographical order is used for elements with equal score.
    };
    private final SortedSet<Map.Entry<Double, String>> scoreUsersSet = new TreeSet<>(comparatorNaturalOrderScoreAndKey);

    @Override
    public Comparator<? super Map.Entry<Double, String>> comparator() {
        return comparatorNaturalOrderScoreAndKey;
    }

    @Override
    public SortedSet<Map.Entry<Double, String>> subSet(Map.Entry<Double, String> fromElement,
                                                       Map.Entry<Double, String> toElement) {
        return scoreUsersSet.subSet(fromElement, toElement);
    }

    @Override
    public SortedSet<Map.Entry<Double, String>> headSet(Map.Entry<Double, String> toElement) {
        return scoreUsersSet.headSet(toElement);
    }

    @Override
    public SortedSet<Map.Entry<Double, String>> tailSet(Map.Entry<Double, String> fromElement) {
        return scoreUsersSet.tailSet(fromElement);
    }

    @Override
    public Map.Entry<Double, String> first() {
        return scoreUsersSet.first();
    }

    @Override
    public Map.Entry<Double, String> last() {
        return scoreUsersSet.last();
    }

    @Override
    public int size() {
        return scoreUsersSet.size();
    }

    @Override
    public boolean isEmpty() {
        return scoreUsersSet.isEmpty();
    }

    @Override
    public boolean contains(Object userKey) {
        return userScoresMap.containsKey(userKey);
    }

    @Override
    public Iterator<Map.Entry<Double, String>> iterator() {
        return scoreUsersSet.iterator();
    }

    @Override
    public Object[] toArray() {
        return scoreUsersSet.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return scoreUsersSet.toArray(a);
    }

    @Override
    public boolean add(Map.Entry<Double, String> scoreUser) {
        if (!userScoresMap.containsKey(scoreUser.getValue())) {
            userScoresMap.put(scoreUser.getValue(), scoreUser.getKey());
            scoreUsersSet.add(scoreUser);
            return true;
        }
        return false;
    }

    @Override
    public boolean remove(Object userKey) {
        if (userScoresMap.containsKey(userKey)) {
            double score = userScoresMap.remove(userKey);
            scoreUsersSet.remove(new SimpleEntry<>(score, userKey));
            return true;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        boolean result = false;
        for (Object object : c) {
            result |= contains(object);
        }
        return result;
    }

    @Override
    public boolean addAll(Collection<? extends Map.Entry<Double, String>> c) {
        boolean result = false;
        for (Map.Entry<Double, String> entry : c) {
            result |= add(entry);
        }
        return result;
    }

    @Override
    public boolean retainAll(Collection<?> userKeys) {
        Set<String> toRemove = new HashSet<>(userScoresMap.keySet());
        toRemove.removeAll(userKeys);
        boolean result = false;
        for (String key : toRemove) {
            result |= remove(key);
        }
        return result;
    }

    @Override
    public boolean removeAll(Collection<?> userKeys) {
        boolean result = false;
        for (Object object : userKeys) {
            result |= remove(object);
        }
        return result;
    }

    @Override
    public void clear() {
        userScoresMap.clear();
        scoreUsersSet.clear();
    }
}
