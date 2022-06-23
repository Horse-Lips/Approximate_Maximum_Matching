package graphUtils;


import java.util.Objects;
import java.util.HashSet;


public class SimpleTuple<X> {
    private X item1;
    private X item2;

    public SimpleTuple(X i1, X i2) {
        this.item1 = i1;
        this.item2 = i2;
    }

    /** Return the first item in the tuple */
    public X getFirst() {
        return this.item1;
    }


    /** Return the second item in the tuple */
    public X getSecond() {
        return this.item2;
    }


    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        
        } else if (!(o instanceof SimpleTuple)) {
            return false;
        
        }

        SimpleTuple t = (SimpleTuple) o;
		
        if (t.getFirst().equals(this.getFirst()) && t.getSecond().equals(this.getSecond())) {
            return true;
        }

        return false;

    }


	@Override
	public int hashCode() {
		HashSet<X> h = new HashSet<X>();

		h.add(this.getFirst());
		h.add(this.getSecond());

		return Objects.hash(h);
	}


	@Override
	public String toString() {
		return "(" + this.item1.toString() + ", " + this.item2.toString() + ")";
	}
}

