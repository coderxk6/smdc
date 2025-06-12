const app = getApp();

Page({
  data: {
    userInfo: { // 默认用户信息，用于未登录或信息为空时显示
      id: null,
      nickName: '未登录用户',
      avatarUrl: '/images/default-avatar.png' // 默认头像路径
    },
    menuList: [{
      icon: '📋', // 使用emoji替代图片
      name: '我的订单',
      path: '/pages/order/list/list'
    }, {
      icon: '📍', // 使用emoji替代图片
      name: '收货地址',
      path: '/pages/address/list/list'
    }, {
      icon: '❤️', // 使用emoji替代图片
      name: '我的收藏',
      path: '/pages/user/favorite/favorite' // 假设收藏页面路径
    }, {
      icon: 'ℹ️', // 使用emoji替代图片
      name: '关于我们',
      path: '/pages/user/about/about' // 假设关于我们页面路径
    }],
    orderCounts: {
      waitPay: 0,
      waitAccept: 0,
      waitServe: 0
    }
  },

  onLoad () {
    // 页面加载时设置导航栏
    wx.setNavigationBarTitle({
      title: '个人中心'
    });
  },

  onShow () {
    // 页面显示时加载用户信息
    this.loadUserInfo();
    // 加载订单数量统计
    this.loadOrderCounts();
  },

  // 加载用户信息
  loadUserInfo () {
    // 首先尝试从本地缓存获取用户信息
    const user = wx.getStorageSync('user');
    if (user && user.id) {
      this.setData({
        userInfo: {
          id: user.id,
          nickName: user.nickName || '用户',
          avatarUrl: user.avatarUrl || '/images/default-avatar.png'
        }
      });
      return; // 如果本地有缓存，直接使用
    }

    // 如果没有本地缓存，尝试从服务器获取
    const token = wx.getStorageSync('token');
    if (!token) {
      // 无token，显示未登录状态
      this.setData({
        userInfo: {
          id: null,
          nickName: '未登录用户',
          avatarUrl: '/images/default-avatar.png'
        }
      });
      return;
    }

    // 有token，从服务器获取用户信息
    wx.request({
      url: `${app.globalData.baseUrl}/mini/user/info`,
      method: 'POST',
      header: {
        'Content-Type': 'application/json',
        'token': token
      },
      success: (res) => {
        if (res.data && res.data.code === 1) {
          const userData = res.data.data;
          // 保存到本地缓存
          wx.setStorageSync('user', userData);
          // 更新页面数据
          this.setData({
            userInfo: {
              id: userData.id,
              nickName: userData.nickName || '用户',
              avatarUrl: userData.avatarUrl || '/images/default-avatar.png'
            }
          });
        } else {
          // 获取失败，可能token失效
          wx.removeStorageSync('token');
          wx.removeStorageSync('user');
          this.setData({
            userInfo: {
              id: null,
              nickName: '未登录用户',
              avatarUrl: '/images/default-avatar.png'
            }
          });
        }
      },
      fail: (err) => {
        console.error('请求用户信息失败:', err);
      }
    });
  },

  // 加载订单数量统计
  loadOrderCounts () {
    const token = wx.getStorageSync('token');
    if (!token) return;

    wx.request({
      url: `${app.globalData.baseUrl}/mini/order/count`,
      method: 'GET',
      header: {
        'token': token
      },
      success: (res) => {
        if (res.data && res.data.code === 1) {
          this.setData({
            orderCounts: res.data.data || {
              waitPay: 0,
              waitAccept: 0,
              waitServe: 0
            }
          });
        }
      }
    });
  },

  // 跳转到对应功能页面
  navigateToPage (e) {
    const path = e.currentTarget.dataset.path;
    if (!this.data.userInfo.id) {
      wx.showToast({
        title: '请先登录',
        icon: 'none'
      });
      setTimeout(() => {
        this.goToLogin();
      }, 1000);
      return;
    }

    if (path) {
      wx.navigateTo({
        url: path
      });
    }
  },

  // 处理用户点击头像/昵称区域
  editUserInfo () {
    if (!this.data.userInfo.id) {
      this.goToLogin();
      return;
    }

    wx.navigateTo({
      url: '/pages/user/info/info'
    });
  },

  // 查看全部订单
  viewAllOrders () {
    if (!this.data.userInfo.id) {
      wx.showToast({
        title: '请先登录',
        icon: 'none'
      });
      setTimeout(() => {
        this.goToLogin();
      }, 1000);
      return;
    }

    wx.navigateTo({
      url: '/pages/order/list/list'
    });
  },

  // 根据订单状态导航
  navigateToOrdersByStatus (e) {
    if (!this.data.userInfo.id) {
      wx.showToast({
        title: '请先登录',
        icon: 'none'
      });
      setTimeout(() => {
        this.goToLogin();
      }, 1000);
      return;
    }

    const status = e.currentTarget.dataset.status;
    wx.navigateTo({
      url: `/pages/order/list/list?status=${status}`
    });
  },

  // 跳转到登录页
  goToLogin () {
    wx.navigateTo({
      url: '/pages/login/login'
    });
  }
}); 