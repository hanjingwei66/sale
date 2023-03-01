import request from '@/utils/request'

// 创建帮助信息
export function createHelpInfo(data) {
  return request({
    url: '/vx/help-info/create',
    method: 'post',
    data: data
  })
}

// 更新帮助信息
export function updateHelpInfo(data) {
  return request({
    url: '/vx/help-info/update',
    method: 'put',
    data: data
  })
}

// 删除帮助信息
export function deleteHelpInfo(id) {
  return request({
    url: '/vx/help-info/delete?id=' + id,
    method: 'delete'
  })
}

// 获得帮助信息
export function getHelpInfo(id) {
  return request({
    url: '/vx/help-info/get?id=' + id,
    method: 'get'
  })
}

// 获得帮助信息分页
export function getHelpInfoPage(query) {
  return request({
    url: '/vx/help-info/page',
    method: 'get',
    params: query
  })
}

// 导出帮助信息 Excel
export function exportHelpInfoExcel(query) {
  return request({
    url: '/vx/help-info/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}
