Page({
  data: {
    userInfo: { // 默认用户信息，用于未登录或信息为空时显示
      nickName: '未登录用户',
      avatarUrl: '/images/default-avatar.png' // 默认头像路径
    },
    menuList: [{
      icon: '/images/order-icon.png', // 我的订单图标
      name: '我的订单',
      path: '/pages/order/list/list'
    }, {
      icon: '/images/address-icon.png', // 收货地址图标
      name: '收货地址',
      path: '/pages/address/list/list'
    }, {
      icon: '/images/favorite-icon.png', // 我的收藏图标
      name: '我的收藏',
      path: '/pages/user/favorite/favorite' // 假设收藏页面路径
    }, {
      icon: '/images/about-icon.png', // 关于我们图标
      name: '关于我们',
      path: '/pages/user/about/about' // 假设关于我们页面路径
    },]
  },

  onShow () {
    // 页面显示时加载用户信息
    this.loadUserInfo();
  },

  // 加载用户信息
  async loadUserInfo () {
    try {
      // 为了让小程序能够正常运行，这里暂时使用一个固定的本地服务地址。
      // 在实际部署时，请替换为您的后端服务地址。
      const baseUrl = 'http://localhost:8080'; // 请替换为您的后端服务地址
      const token = wx.getStorageSync('token') || ''; // 从缓存中获取token

      const res = await wx.request({
        url: `${baseUrl}/mini/user/info`,
        method: 'POST', // 根据接口规范，获取用户信息是POST请求
        header: {
          'Content-Type': 'application/json',
          'token': token
        }
      });

      if (res && res.data && res.data.code === 1) {
        // 如果成功获取到用户信息，更新页面数据
        this.setData({
          userInfo: {
            nickName: res.data.data.nickName || '用户',
            avatarUrl: res.data.data.avatarUrl || '/images/default-avatar.png'
          }
        });
      } else {
        // 获取失败，可能未登录、token失效或后端返回非预期数据，显示默认信息
        wx.showToast({
          title: (res && res.data && res.data.msg) || '获取用户信息失败',
          icon: 'none'
        });
        this.setData({
          userInfo: {
            nickName: '未登录用户',
            avatarUrl: '/images/default-avatar.png'
          }
        });
      }
    } catch (error) {
      console.error('请求用户信息失败:', error);
      wx.showToast({
        title: '网络请求失败',
        icon: 'none'
      });
      this.setData({
        userInfo: {
          nickName: '未登录用户',
          avatarUrl: '/images/default-avatar.png'
        }
      });
    }
  },

  // 跳转到对应功能页面
  navigateToPage (e) {
    const path = e.currentTarget.dataset.path;
    if (path) {
      wx.navigateTo({
        url: path
      });
    }
  },

  // 处理用户点击头像/昵称区域，例如跳转到个人资料编辑页
  editUserInfo () {
    wx.navigateTo({
      url: '/pages/user/info/info' // 假设个人资料编辑页路径
    });
  }
}); 