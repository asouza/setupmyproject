package com.setupmyproject.commands;

import static com.google.common.base.Preconditions.checkState;
import static com.google.common.collect.Iterables.get;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.difference;
import static com.google.common.collect.Sets.newHashSet;
import static com.google.common.collect.Sets.union;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

//copiado do vraptor3.
/**
 * Usado para rodar os comandos em ordem
 * @author alberto
 *
 * @param <E>
 */
public class Graph<E> {

	private final E DUMMY = null;
	private Multimap<E, E> graph = LinkedHashMultimap.create();
	private List<E> orderedList;

	private final Lock lock = new ReentrantLock();

	public void addEdge(E from, E to) {
		checkState(orderedList == null, "You shouldn't add more commands after ordering.");
		graph.put(from, to);
	}

	public void addEdges(E from, Collection<E> tos) {
		for (E to : tos) {
			addEdge(from, to);
		}
	}

	public List<E> topologicalOrder() {
		if (orderedList == null) {
			lock.lock();
			try {
				if (orderedList == null) {
					this.orderedList = orderTopologically();
				}
			} finally {
				lock.unlock();
			}
		}
		return this.orderedList;
	}

	private List<E> orderTopologically() {
		List<E> list = newArrayList();
		addDummies();		
		while(!graph.keySet().isEmpty()) {
			Set<E> roots = findRoots();
			if (roots.isEmpty()) {
				throw new IllegalStateException("There is a cycle on the interceptor sequence: \n" + cycle());
			}

			list.addAll(roots);
			removeRoots(roots);
		}
		return list;
	}

	private void addDummies() {
		Set<E> nodes = union(graph.keySet(), newHashSet(graph.values()));
		for (E node : nodes) {
			graph.put(node, DUMMY);
		}
	}

	private void removeRoots(Set<E> roots) {
		for (E root : roots) {
			graph.removeAll(root);
		}
	}

	private Set<E> findRoots() {
		return difference(graph.keySet(), newHashSet(graph.values())).immutableCopy();
	}

	private String cycle() {
		removeLeaves();

		return findCycle().toString();
	}

	private List<E> findCycle() {
		E node = get(graph.keySet(), 0);
		List<E> cycle = Lists.newArrayList();
		do {
			cycle.add(node);
		} while(!cycle.contains(node = get(graph.get(node), 0)));

		return cycle.subList(cycle.indexOf(node), cycle.size());
	}

	private void removeLeaves() {
		Set<E> leaves = findLeaves();
		if (leaves.isEmpty()) {
			return;
		}

		for (E key : newHashSet(graph.keySet())) {
			for (E value : leaves) {
				graph.remove(key, value);
			}
		}
		removeLeaves();
	}

	private Set<E> findLeaves() {
		return difference(newHashSet(graph.values()), graph.keySet());
	}
}
