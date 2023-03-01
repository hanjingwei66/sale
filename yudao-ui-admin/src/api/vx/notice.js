import request from '@/utils/request'

// 创建公告栏
export function createNotice(data) {
  return request({
    url: '/vx/notice/create',
    method: 'post',
    data: data
  })
}

// 更新公告栏
export function updateNotice(data) {
  return request({
    url: '/vx/notice/update',
    method: 'put',
    data: data
  })
}

// 删除公告栏
export function deleteNotice(id) {
  return request({
    url: '/vx/notice/delete?id=' + id,
    method: 'delete'
  })
}

// 获得公告栏
export function getNotice(id) {
  return request({
    url: '/vx/notice/get?id=' + id,
    method: 'get'
  })
}

// 获得公告栏分页
export function getNoticePage(query) {
  return request({
    url: '/vx/notice/page',
    method: 'get',
    params: query
  })
}

// 导出公告栏 Excel
export function exportNoticeExcel(query) {
  return request({
    url: '/vx/notice/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}
