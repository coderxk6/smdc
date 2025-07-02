App({
  globalData: {
    baseUrl: 'http://localhost:8080', // 开发环境服务地址
    user: null,
    token: null,
    // 常用图片资源
    images: {
      defaultAvatar: '/images/default-avatar.png',
      defaultLogo: '/images/default-logo.png',
      placeholder: '/images/placeholder.png'
    },
    // 常用图标（使用emoji替代图片）
    icons: {
      order: '📋',
      address: '📍',
      favorite: '❤️',
      about: 'ℹ️',
      user: '👤',
      cart: '🛒',
      home: '🏠',
      search: '🔍'
    }
  },

  onLaunch () {
    console.log('App Launch');

    // 从本地存储恢复数据
    this.globalData.user = wx.getStorageSync('user') || null;
    this.globalData.token = wx.getStorageSync('token') || null;

    // 监听网络状态变化
    wx.onNetworkStatusChange(res => {
      if (!res.isConnected) {
        wx.showToast({
          title: '网络连接已断开',
          icon: 'none',
          duration: 2000
        });
      }
    });

    // 检查网络状态
    wx.getNetworkType({
      success: res => {
        if (res.networkType === 'none') {
          wx.showToast({
            title: '当前无网络连接',
            icon: 'none',
            duration: 2000
          });
        }
      }
    });

    // 全局错误监听
    wx.onError(err => {
      console.error('小程序全局错误:', err);
      // 可以在这里添加错误上报逻辑
    });

    // 页面不存在监听
    wx.onPageNotFound(res => {
      console.error('页面不存在:', res.path);
      wx.switchTab({
        url: '/pages/index/index',
        fail: () => {
          wx.redirectTo({
            url: '/pages/index/index'
          });
        }
      });
    });
  },

  // 获取用户信息
  getUserInfo () {
    return new Promise((resolve, reject) => {
      if (this.globalData.user) {
        resolve(this.globalData.user);
        return;
      }

      const token = this.globalData.token || wx.getStorageSync('token');
      if (!token) {
        reject(new Error('未登录'));
        return;
      }

      wx.request({
        url: `${this.globalData.baseUrl}/mini/user/info`,
        method: 'POST',
        header: {
          'Content-Type': 'application/json',
          'token': token
        },
        success: res => {
          if (res.data && res.data.code === 1) {
            const user = res.data.data;
            this.globalData.user = user;
            wx.setStorageSync('user', user);
            resolve(user);
          } else {
            this.globalData.user = null;
            this.globalData.token = null;
            wx.removeStorageSync('user');
            wx.removeStorageSync('token');
            reject(new Error(res.data.msg || '获取用户信息失败'));
          }
        },
        fail: err => {
          console.error('获取用户信息请求失败:', err);
          reject(err);
        }
      });
    });
  },

  // 判断用户是否已登录
  isLoggedIn () {
    return !!this.globalData.token || !!wx.getStorageSync('token');
  },

  // 用户登出
  logout () {
    this.globalData.user = null;
    this.globalData.token = null;
    wx.removeStorageSync('user');
    wx.removeStorageSync('token');

    wx.showToast({
      title: '已退出登录',
      icon: 'success',
      duration: 2000
    });

    // 重定向到登录页面
    setTimeout(() => {
      wx.reLaunch({
        url: '/pages/login/login'
      });
    }, 1500);
  }
}); 