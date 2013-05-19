package yoshikihigo.tinypdg.cfg.node;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import yoshikihigo.tinypdg.pe.ExpressionInfo;
import yoshikihigo.tinypdg.pe.ProgramElementInfo;

public class CFGNodeFactory {

	private final ConcurrentMap<ProgramElementInfo, CFGNode<? extends ProgramElementInfo>> elementToNodeMap;

	public CFGNodeFactory() {
		this.elementToNodeMap = new ConcurrentHashMap<ProgramElementInfo, CFGNode<? extends ProgramElementInfo>>();
	}

	public synchronized CFGNode<? extends ProgramElementInfo> makeNormalNode(
			final ProgramElementInfo element) {

		if (null == element) {
			return new CFGPseudoNode();
		}

		CFGNormalNode node = (CFGNormalNode) this.elementToNodeMap.get(element);
		if (null == node) {
			node = new CFGNormalNode(element);
			this.elementToNodeMap.put(element, node);
		}
		return node;
	}

	public synchronized CFGNode<? extends ProgramElementInfo> makeControlNode(
			final ExpressionInfo expression) {

		if (null == expression) {
			return new CFGPseudoNode();
		}

		CFGControlNode node = (CFGControlNode) this.elementToNodeMap
				.get(expression);
		if (null == node) {
			node = new CFGControlNode(expression);
			this.elementToNodeMap.put(expression, node);
		}
		return node;
	}

	public synchronized boolean removeNode(final ProgramElementInfo element) {
		return null != this.elementToNodeMap.remove(element) ? true : false;
	}

	public SortedSet<CFGNode<? extends ProgramElementInfo>> getAllNodes() {
		final SortedSet<CFGNode<? extends ProgramElementInfo>> nodes = new TreeSet<CFGNode<? extends ProgramElementInfo>>();
		nodes.addAll(this.elementToNodeMap.values());
		return nodes;
	}
}
