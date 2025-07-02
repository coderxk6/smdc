import store from './store';

const BASE_URL = 'http://localhost:8080'; // 配置基础URL

const request = (url, options = {}) => {
  const token = wx.getStorageSync('token');

  // 显示加载状态
  store.set('loading', true);

  return new Promise((resolve, reject) => {
    wx.request({
      url: `${BASE_URL}${url}`,
      ...options,
      header: {
        'Content-Type': 'application/json',
        'Authorization': token ? `Bearer ${token}` : '',
        ...options.header
      },
      success: (res) => {
        if (res.statusCode === 200) {
          if (res.data.code === 1) {
            resolve(res.data.data);
          } else {
            wx.showToast({
              title: res.data.msg || '请求失败',
              icon: 'none'
            });
            reject(new Error(res.data.msg));
          }
        } else if (res.statusCode === 401) {
          // token失效，跳转登录
          wx.navigateTo({
            url: '/pages/login/login'
          });
          reject(new Error('未登录或登录已过期'));
        } else {
          wx.showToast({
            title: '服务器错误',
            icon: 'none'
          });
          reject(new Error('服务器错误'));
        }
      },
      fail: (err) => {
        wx.showToast({
          title: '网络错误',
          icon: 'none'
        });
        reject(err);
      },
      complete: () => {
        store.set('loading', false);
      }
    });
  });
};

// GET请求
const get = (url, data = {}) => {
  return request(url, {
    method: 'GET',
    data
  });
};

// POST请求
const post = (url, data = {}) => {
  return request(url, {
    method: 'POST',
    data
  });
};

export default {
  get,
  post
}; 