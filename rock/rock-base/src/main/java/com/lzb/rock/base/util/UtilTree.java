package com.lzb.rock.base.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.lzb.rock.base.model.TreeNode;

/**
 * 树节点帮助类
 * 
 * @author lzb
 * @Date 2019年9月29日 下午2:01:45
 */
public class UtilTree {

	/**
	 * 创建顶级节点
	 * 
	 * @return
	 */
	public static TreeNode createTopNode() {
		TreeNode treeNode = new TreeNode();
		treeNode.setId("-1");
		treeNode.setPId("-2");
		treeNode.setTitle("顶级");
		return treeNode;
	}

	/**
	 * 组装树节点
	 * 
	 * @param data
	 * @return
	 */
	public static List<TreeNode> assembleTree(List<TreeNode> data) {
		// 查找跟节点
		List<TreeNode> tree = new ArrayList<TreeNode>();
		for (TreeNode treeNode : data) {
			if (treeNode.getId().equals("-1")) {
				tree.add(treeNode);
			}
		}
		for (TreeNode treeNode : tree) {
			getChildren(data, treeNode);
		}

		return tree;
	}

	/**
	 * 获取节点的子节点
	 * 
	 * @param data
	 * @param node
	 * @return
	 */
	public static void getChildren(List<TreeNode> data, TreeNode node) {
		List<TreeNode> children = new ArrayList<TreeNode>();
		Boolean flag = false;
		for (TreeNode treeNode : data) {
			if (node.getId().equals(treeNode.getPId())) {
				if (UtilString.isNotBlank(node.getCode()) && UtilString.isNotBlank(treeNode.getCode())
						&& node.getCode().equals(treeNode.getCode())) {
					flag = true;
				} else if (UtilString.isBlank(node.getCode()) && UtilString.isBlank(treeNode.getCode())) {
					flag = true;
				} else {
					flag = false;
				}
				if (flag) {
					children.add(treeNode);
				}
			}
		}
		// 重新排序
		Collections.sort(children);
		node.setChildren(children);
		// 查找子节点
		for (TreeNode treeNode : children) {
			getChildren(data, treeNode);
		}
	}
}
