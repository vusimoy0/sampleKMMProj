package za.co.kernelpanic.food2forkkmm.domain.util

class Queue<T>(list: MutableList<T>) {

    var items: MutableList<T> = list

    fun isEmpty(): Boolean = items.isEmpty()

    fun count(): Int = items.count()

    override fun toString(): String = items.toString()

    fun add(element: T) {
        items.add(element)
    }

    fun pop(item: T): Boolean {
        return items.remove(item)
    }

    @Throws(NoSuchElementException::class)
    fun remove(): T {
        if (this.isEmpty()) {
            throw NoSuchElementException("method 'remove' threw an exception. Nothing to remove from the queue")
        } else {
            return items.removeAt(index = 0)
        }
    }

    @Throws(NoSuchElementException::class)
    fun element(): T {
        if (this.isEmpty()) {
            throw NoSuchElementException("fun 'element threw an exception. Nothing in the queue")
        }
        return items[0]
    }

    fun offer(element: T): Boolean {
        return try {
            items.add(element)
        } catch (e: Exception) {
            false
        }
    }

    fun poll(): T? {
        if (this.isEmpty()) return null
        return items.removeAt(0)
    }

    fun peek(): T? {
        if (this.isEmpty()) return null
        return items[0]
    }

    fun addAll(queue: Queue<T>) {
        this.items.addAll(queue.items)
    }

    fun clear() {
        items.removeAll { true }
    }
}