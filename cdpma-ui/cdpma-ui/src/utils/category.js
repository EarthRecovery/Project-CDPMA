// 查找给定 categoryId 的路径
export function findCategoryPath(categoryTree, categoryId) {
  const path = findPathInTree(categoryTree, categoryId);
  if (path) {
    return path;
  } else {
    return [];
  }
}

export function buildCategoryTree (categories){
  const map = {}
  const tree = []
  
  // 构建一个 id->category 映射
  categories.forEach(category => {
    map[category.categoryId] = { ...category, children: [] }
  })
  
  categories.forEach(category => {
    if (category.parentCategory === -1) {
      // 根分类直接放到树形结构中
      tree.push(map[category.categoryId])
    } else {
      // 子分类挂到父分类下
      if (map[category.parentCategory]) {
        map[category.parentCategory].children.push(map[category.categoryId])
      }
    }
  })
  
  return tree
}

// 递归查找路径
export function findPathInTree(tree, categoryId, path = []) {
  for (const category of tree) {
    // 将当前 categoryId 加入路径
    const currentPath = [...path, category.categoryId];

    // 如果找到了 categoryId，返回路径
    if (category.categoryId === categoryId) {
      return currentPath;
    }

    // 如果有子分类，递归查找
    if (category.children && category.children.length > 0) {
      const result = findPathInTree(category.children, categoryId, currentPath);
      if (result) {
        return result;
      }
    }
  }
  return null; // 如果没有找到，返回 null
}
