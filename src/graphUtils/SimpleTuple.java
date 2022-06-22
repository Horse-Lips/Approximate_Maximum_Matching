package graphUtils;


public class SimpleTuple<X, Y> {
    private X item1;
    private Y item2;

    public SimpleTuple(X i1, Y i2) {
        this.item1 = i1;
        this.item2 = i2;
    }

    /** Return the first item in the tuple */
    public X getFirst() {
        return this.item1;
    }


    /** Return the second item in the tuple */
    public Y getSecond() {
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
		return (Integer) this.getFirst() - (Integer) this.getSecond();
	}


	@Override
	public String toString() {
		return "(" + this.item1.toString() + ", " + this.item2.toString() + ")";
	}
}

