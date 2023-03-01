import request from '@/utils/request'

// 创建试药项目
export function createProject(data) {
  return request({
    url: '/vx/project/create',
    method: 'post',
    data: data
  })
}

// 更新试药项目
export function updateProject(data) {
  return request({
    url: '/vx/project/update',
    method: 'put',
    data: data
  })
}

// 删除试药项目
export function deleteProject(id) {
  return request({
    url: '/vx/project/delete?id=' + id,
    method: 'delete'
  })
}

// 获得试药项目
export function getProject(id) {
  return request({
    url: '/vx/project/get?id=' + id,
    method: 'get'
  })
}

// 获得试药项目分页
export function getProjectPage(query) {
  return request({
    url: '/vx/project/page',
    method: 'get',
    params: query
  })
}

// 导出试药项目 Excel
export function exportProjectExcel(query) {
  return request({
    url: '/vx/project/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}
