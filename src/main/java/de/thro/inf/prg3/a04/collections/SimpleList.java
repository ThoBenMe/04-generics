package de.thro.inf.prg3.a04.collections;

import java.util.function.Function;

public interface SimpleList<T> extends Iterable<T>{
	/**
	 * Add a given object to the back of the list.
	 */
	void add(T o);

	default void addDefault(Class<T> clazz){
		try {
			this.add(clazz.newInstance());
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return current size of the list
	 */
	int size();

	/**
	 * Get a new SimpleList instance with all items of this list which match the given filter
	 * @param filter SimpleFilter instance
	 * @return new SimpleList instance
	 */
	@SuppressWarnings("unchecked")
	default SimpleList<T> filter(SimpleFilter<T> filter){
		SimpleList<T> result;
		try{
			result = (SimpleList<T>)getClass().newInstance();
		} catch (IllegalAccessException | InstantiationException e){
			result = new SimpleListImpl<>();
		}

		for(T o : this){
			if(filter.include(o)){
				result.add(o);
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	default<R> SimpleList<R> map(Function<T,R> transform){
		SimpleList<R> result;
		try{
			result = (SimpleList<R>)getClass().newInstance();
		} catch (InstantiationException | IllegalAccessException e){
			result = new SimpleListImpl<>();
		}
		for(T t : this){
			result.add(transform.apply(t));
		}
		return result;
	}
}
