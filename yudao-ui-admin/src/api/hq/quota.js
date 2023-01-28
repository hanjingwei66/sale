import request from '@/utils/request'

// 创建指标
export function createQuota(data) {
  return request({
    url: '/hq/quota/create',
    method: 'post',
    data: data
  })
}

// 更新指标
export function updateQuota(data) {
  return request({
    url: '/hq/quota/update',
    method: 'put',
    data: data
  })
}
export function createAndUpdateQuota(data) {
  return request({
    url: '/hq/quota/createAndUpdate',
    method: 'post',
    data: data
  })
}
// 删除指标
export function deleteQuota(id) {
  return request({
    url: '/hq/quota/delete?id=' + id,
    method: 'delete'
  })
}

// 获得指标
export function getQuota(id) {
  return request({
    url: '/hq/quota/get?id=' + id,
    method: 'get'
  })
}
// 获得指标
export function getBySaleId(id) {
  return request({
    url: '/hq/quota/getBySaleId?id=' + id,
    method: 'get'
  })
}

// 获得指标分页
export function getQuotaPage(query) {
  return request({
    url: '/hq/quota/page',
    method: 'get',
    params: query
  })
}

// 导出指标 Excel
export function exportQuotaExcel(query) {
  return request({
    url: '/hq/quota/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}
