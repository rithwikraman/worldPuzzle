package hash;


public class HashTable<K,V> implements Map<K,V>{

	private HashNode<K,V>[] table;
	private static int INITIAL_CAP = 10;
	private int size;
	private HashNode<K,V> sentinel; 
	private double loadFactor;
	public static int collLin;
	public static int collQuad;
		
	public HashTable() {
		this(INITIAL_CAP);
	}
	
	@SuppressWarnings("unchecked")
	public HashTable(int initialCapacity) {
		this.table = (HashNode<K,V>[]) new HashNode[initialCapacity];
		this.size = 0;
		this.sentinel = new HashNode<K, V>(null,null);
		this.loadFactor = 0.0;
		collLin = 0;
		collQuad = 0;
	}
	
	@SuppressWarnings("unchecked")
	public void resize(int newCapacity) {
		HashNode<K,V>[] oldTable = this.table;
		this.table = new HashNode[newCapacity];
		this.size = 0;
		for (int i = 0; i < oldTable.length; i++) {
			if (oldTable[i] != null && oldTable[i] != sentinel) { 
				this.insert(oldTable[i].getKey(), oldTable[i].getValue());
			}
		}
	}
	
	@Override
	public void insert(K key, V value) {
		if (this.loadFactor >= 0.75) {
			resize(this.table.length * 2);
		}
		HashNode<K,V> val = new HashNode<K, V>(key,value);
		int index = Math.abs(key.hashCode()) % this.table.length;
		while (this.table[index] != null && !(val.equals(this.table[index])) && this.table[index] != sentinel) {
			index = (index + 1) % this.table.length;
			//collLin++;
			//if (index >= this.table.length) {index = 0;}
		}
		table[index] = val;
		size++;
		this.loadFactor = (double) size / (table.length);
		
	}
	
	@Override
	public V retrieve(K key) {
		if (contains(key)) {
			int index = Math.abs(key.hashCode() % this.table.length);
			while (this.table[index] != null) {
				if (key.equals(this.table[index].getKey())) {
					return this.table[index].getValue();
				}
				else {
					index++;
					if (index >= this.table.length) {index = 0;}
				}
			}
		}
		return null;
	}

	@Override
	public boolean contains(K key) {
		int index = Math.abs(key.hashCode() % this.table.length);
		while (this.table[index] != null) {
			if (key.equals(table[index].getKey())) {
				return true;
			}
			else {
				index++;
				if (index >= this.table.length) {index = 0;}
			} 
		}
		return false;
	}
	
	@Override
	public void remove(K key) {
		if (contains(key)) {
			int index = Math.abs(key.hashCode() % this.table.length);
			while (this.table[index] != null) {
				if (key.equals(this.table[index].getKey())) {
					this.table[index] = sentinel;
				}
				else {
					index++;
					if (index >= this.table.length) {index = 0;}
				}
			}
			size--;
			this.loadFactor = (double) size / this.table.length;
		}
	}
	
}
