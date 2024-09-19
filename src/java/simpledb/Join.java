package simpledb;
import java.util.*;

/**
 * The Join operator implements the relational join operation.
 */

public class Join extends Operator {

	private final JoinPredicate join;
	private final DbIterator child1, child2;
	private Tuple tup1;
	private int state = 0;
	private final List<Tuple> cache = new ArrayList<>();
	private boolean cacheAvailable = false;
	
    /**
     * Constructor.  Accepts to children to join and the predicate
     * to join them on
     *
     * @param join The predicate to use to join the children
     * @param child1 Iterator for the left(outer) relation to join
     * @param child2 Iterator for the right(inner) relation to join
     */
    public Join(JoinPredicate join, DbIterator child1, DbIterator child2) {
        // Done
    	this.join = join;
    	this.child1 = child1;
    	this.child2 = child2;
    }

    /**
     * @see simpledb.TupleDesc#merge(TupleDesc, TupleDesc) for possible implementation logic.
     */
    public TupleDesc getTupleDesc() {
    	// Done
    	return TupleDesc.merge(child1.getTupleDesc(), child2.getTupleDesc());
    }

    public void open()
        throws DbException, NoSuchElementException, TransactionAbortedException {
        // Done
    	child1.open();
    	child2.open();
    	tup1 = child1.hasNext()? child1.next() : null;
    	state = 0;
    }

    public void close() {
        // Done
    	child1.close();
    	child2.close();
    	tup1 = null;
    }

    public void rewind() throws DbException, TransactionAbortedException {
        // Done
    	child1.rewind();
    	child2.rewind();
        tup1 = child1.hasNext()? child1.next() : null;
        state = 0;
    }

    /**
     * Returns the next tuple generated by the join, or null if there are no more tuples.
     * Logically, this is the next tuple in r1 cross r2 that satisfies the join
     * predicate.  There are many possible implementations; the simplest is a
     * nested loops join.
     * <p>
     * Note that the tuples returned from this particular implementation of
     * Join are simply the concatenation of joining tuples from the left and
     * right relation. Therefore, if an equality predicate is used 
     * there will be two copies of the join attribute
     * in the results.  (Removing such duplicate columns can be done with an
     * additional projection operator if needed.)
     * <p>
     * For example, if one tuple is {1,2,3} and the other tuple is {1,5,6},
     * joined on equality of the first column, then this returns {1,2,3,1,5,6}.
     *
     * @return The next matching tuple.
     * @see JoinPredicate#filter
     */
    protected Tuple fetchNext() throws TransactionAbortedException, DbException {
        // Done
        if (!cacheAvailable) {
            final Tuple tuple = doFetchNext();
            if (cache.size() <= state) {
                cache.add(tuple);
            }
        }
        if (null == cache.get(state)) {
            cacheAvailable = true;
            return null;
        } else {
            return cache.get(state++);
        }
    }

    private Tuple doFetchNext() throws TransactionAbortedException, DbException {
        if (null == tup1) {
            return null;
        }
        while (true) {
            while (!child2.hasNext()) {
                if (!child1.hasNext()) {
                    tup1 = null;
                    return null;
                } else {
                    tup1 = child1.next();
                    child2.rewind();
                }
            }
            Tuple tup2 = child2.next();
            if (join.filter(tup1, tup2)) {
                Tuple tup = new Tuple(getTupleDesc());
                int i = 0;
                for (int j = 0; j < tup1.getTupleDesc().numFields(); j++) {
                    tup.setField(i++, tup1.getField(j));
                }
                for (int j = 0; j < tup2.getTupleDesc().numFields(); j++) {
                    tup.setField(i++, tup2.getField(j));
                }
                return tup;
            }
        }
    }
}