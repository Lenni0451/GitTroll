package net.Lenni0451.GitTroll.utils;

public class ArrayHelper {
	
	public static ArrayHelper empty() {
		return new ArrayHelper(new Object[] {});
	}
	
	
	private Object[] array;

	public ArrayHelper(final Object[] array) {
		this.array = array;
	}

	public int getLength() {
		return this.array.length;
	}

	public boolean isLength(final int length) {
		return this.getLength() == length;
	}

	public boolean isSmaller(final int length) {
		return this.getLength() < length;
	}

	public boolean isLarger(final int length) {
		return this.getLength() > length;
	}

	public boolean isEmpty() {
		return this.getLength() == 0;
	}

	public boolean isIndexValid(final int index) {
		return index >= 0 && index < this.getLength();
	}

	public Object get(final int index) {
		if(!this.isIndexValid(index)) {
			return null;
		}

		return this.array[index];
	}


	public boolean isString(final int index) {
		if(!this.isIndexValid(index)) {
			return false;
		}

		return this.get(index) instanceof String;
	}

	public boolean isBoolean(final int index) {
		if(!this.isIndexValid(index)) {
			return false;
		}

		try {
			Boolean.valueOf(this.getString(index));
			return true;
		} catch (Exception e) {}

		return false;
	}

	public boolean isChar(final int index) {
        if(!this.isIndexValid(index)) {
            return false;
        }

        try {
            @SuppressWarnings("unused")
			char c = (char) this.get(index);
            return true;
        } catch (Exception e) {}

        return false;
    }

	public boolean isShort(final int index) {
		if(!this.isIndexValid(index)) {
			return false;
		}

		try {
			Short.valueOf(this.get(index).toString());
			return true;
		} catch (Exception e) {}

		return false;
	}

	public boolean isInteger(final int index) {
		if(!this.isIndexValid(index)) {
			return false;
		}

		try {
			Integer.valueOf(this.get(index).toString());
			return true;
		} catch (Exception e) {}

		return false;
	}

	public boolean isLong(final int index) {
		if(!this.isIndexValid(index)) {
			return false;
		}

		try {
			Long.valueOf(this.get(index).toString());
			return true;
		} catch (Exception e) {}

		return false;
	}

	public boolean isFloat(final int index) {
		if(!this.isIndexValid(index)) {
			return false;
		}

		try {
			Float.valueOf(this.get(index).toString());
			return true;
		} catch (Exception e) {}

		return false;
	}

	public boolean isDouble(final int index) {
		if(!this.isIndexValid(index)) {
			return false;
		}

		try {
			Double.valueOf(this.get(index).toString());
			return true;
		} catch (Exception e) {}

		return false;
	}


	public String getString(final int index, final String standart) {
		if(!this.isIndexValid(index) || !this.isString(index)) {
			return standart;
		}

		return this.get(index).toString();
	}

	public boolean getBoolean(final int index, final boolean standart) {
	    if(!this.isIndexValid(index) || !this.isBoolean(index)) {
	        return standart;
        }

        return Boolean.valueOf(this.getString(index));
    }

    public char getChar(final int index, final char standart) {
	    if(!this.isIndexValid(index) || !this.isChar(index)) {
	        return standart;
        }

        return (char) this.get(index);
    }

	public short getShort(final int index, final short standart) {
		if(!this.isIndexValid(index) || !this.isShort(index)) {
			return standart;
		}

		return Short.valueOf(this.get(index).toString());
	}

	public int getInteger(final int index, final int standart) {
		if(!this.isIndexValid(index) || !this.isInteger(index)) {
			return standart;
		}

		return Integer.valueOf(this.get(index).toString());
	}

	public long getLong(final int index, final long standart) {
		if(!this.isIndexValid(index) || !this.isLong(index)) {
			return standart;
		}

		return Long.valueOf(this.get(index).toString());
	}

	public float getFloat(final int index, final float standart) {
		if(!this.isIndexValid(index) || !this.isFloat(index)) {
			return standart;
		}

		return Float.valueOf(this.get(index).toString());
	}

	public double getDouble(final int index, final double standart) {
		if(!this.isIndexValid(index) || !this.isDouble(index)) {
			return standart;
		}

		return Double.valueOf(this.get(index).toString());
	}


	public String getString(final int index) {
		return this.getString(index, "");
	}

	public boolean getBoolean(final int index) {
	    return this.getBoolean(index, false);
    }

    public char getChar(final int index) {
	    return this.getChar(index, "A".toCharArray()[0]);
    }

	public short getShort(final int index) {
		return this.getShort(index, (short) 0);
	}

	public int getInteger(final int index) {
		return this.getInteger(index, 0);
	}

	public long getLong(final int index) {
		return this.getLong(index, 0);
	}

	public float getFloat(final int index) {
		return this.getFloat(index, 0);
	}

	public double getDouble(final int index) {
		return this.getDouble(index, 0);
	}

	public void add(final Object object, final Object... objects) {
		this.array = this.advance(object, objects);
	}


	public Object[] advance(final Object obToAdd, final Object... obs) {
		Object[] newArray = new Object[this.getLength() + 1 + obs.length];

		int i = 0;
		for(Object ob : this.array) {
			newArray[i] = ob;

			i++;
		}
		newArray[i] = obToAdd;
		i++;
		for(Object ob : obs) {
			newArray[i] = ob;
			i++;
		}

		return newArray;
	}

	public String[] advanceToStrings(final String strToAdd, final String... strs) {
		String[] newArray = new String[this.getLength() + 1 + strs.length];

		int i = 0;
		for(Object ob : this.array) {
			newArray[i] = ob.toString();

			i++;
		}
		newArray[i] = strToAdd;
		i++;
		for(String str : strs) {
			newArray[i] = str;
			i++;
		}

		return newArray;
	}
	
	public <T> T[] getAsArray() {
		return (T[]) this.array;
	}
	
	public String getAsString() {
		return this.getAsString(0, " ");
	}
	
	public String getAsString(final String combiner) {
		return this.getAsString(0, combiner);
	}
	
	public String getAsString(final int start) {
		return this.getAsString(start, " ");
	}
	
	public String getAsString(final int start, final String combiner) {
		return this.getAsString(start, this.getLength() - 1, combiner);
	}
	
	public String getAsString(final int start, final int end) {
		return this.getAsString(start, end, " ");
	}
	
	public String getAsString(int start, int end, final String combiner) {
		if(start < 0) {
			start = 0;
		}
		if(end > this.getLength() - 1) {
			end = this.getLength() - 1;
		}
		if(end < start) {
			return "";
		}
		
		String out = "";
		for(int i = start; i <= end; i++) {
			if(out.isEmpty()) {
				out = this.getString(i);
			} else {
				out += combiner + this.getString(i);
			}
		}
		return out;
	}


	@Override
    public String toString() {
	    String complete = "";
	    for(int i = 0; i < this.array.length; i++) {
	        complete += (complete.isEmpty()?"":", ") + this.array[i];
        }
        return "[" + complete + "]";
    }
	
}
