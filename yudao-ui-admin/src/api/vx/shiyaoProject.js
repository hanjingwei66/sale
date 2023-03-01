import request from '@/utils/request'

// 创建试药项目
export function createShiyaoProject(data) {
  return request({
    url: '/vx/shiyao-project/create',
    method: 'post',
    data: data
  })
}

// 更新试药项目
export function updateShiyaoProject(data) {
  return request({
    url: '/vx/shiyao-project/update',
    method: 'put',
    data: data
  })
}

// 删除试药项目
export function deleteShiyaoProject(id) {
  return request({
    url: '/vx/shiyao-project/delete?id=' + id,
    method: 'delete'
  })
}

// 获得试药项目
export function getShiyaoProject(id) {
  return request({
    url: '/vx/shiyao-project/get?id=' + id,
    method: 'get'
  })
}

// 获得试药项目分页
export function getShiyaoProjectPage(query) {
  return request({
    url: '/vx/shiyao-project/page',
    method: 'get',
    params: query
  })
}

// 导出试药项目 Excel
export function exportShiyaoProjectExcel(query) {
  return request({
    url: '/vx/shiyao-project/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}
export function getProjectList() {
  return request({
    url: '/vx/shiyao-project/getList',
    method: 'get'
  })
}
