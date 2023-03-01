import request from '@/utils/request'

// 创建微信用户信息
export function createUser(data) {
  return request({
    url: '/vx/user/create',
    method: 'post',
    data: data
  })
}

// 更新微信用户信息
export function updateUser(data) {
  return request({
    url: '/vx/user/update',
    method: 'put',
    data: data
  })
}

// 删除微信用户信息
export function deleteUser(id) {
  return request({
    url: '/vx/user/delete?id=' + id,
    method: 'delete'
  })
}

// 获得微信用户信息
export function getUser(id) {
  return request({
    url: '/vx/user/get?id=' + id,
    method: 'get'
  })
}

// 获得微信用户信息分页
export function getUserPage(query) {
  return request({
    url: '/vx/user/page',
    method: 'get',
    params: query
  })
}

// 导出微信用户信息 Excel
export function exportUserExcel(query) {
  return request({
    url: '/vx/user/export-excel',
    method: 'get',
    params: query,
    responseType: 'blob'
  })
}
export function signUpUser(data) {
  return request({
    url: '/vx/user/signUp',
    method: 'post',
    data: data
  })
}
