const app = getApp();

Page({
  data: {
    activeTab: 0, // 默认选中微信登录
    username: '',
    password: ''
  },

  onLoad () {
    // 自动填充测试账号
    this.setData({
      username: 'test',
      password: '123456'
    });
  },

  switchTab (e) {
    this.setData({
      activeTab: Number(e.currentTarget.dataset.index)
    });
  },

  onInputUsername (e) {
    this.setData({ username: e.detail.value });
  },

  onInputPassword (e) {
    this.setData({ password: e.detail.value });
  },

  // 微信登录
  onWechatLogin (e) {
    if (e.detail.errMsg === 'getPhoneNumber:ok') {
      wx.showLoading({ title: '登录中...' });

      // 调用微信登录接口
      wx.login({
        success: (res) => {
          if (res.code) {
            // 发送 code 到后台换取 openId, sessionKey, unionId
            wx.request({
              url: `${app.globalData.baseUrl}/mini/user/login`,
              method: 'POST',
              data: {
                code: res.code,
                encryptedData: e.detail.encryptedData,
                iv: e.detail.iv
              },
              success: (result) => {
                wx.hideLoading();
                if (result.data.code === 1 && result.data.data) {
                  wx.setStorageSync('user', result.data.data.user);
                  wx.setStorageSync('token', result.data.data.token);
                  wx.showToast({ title: '登录成功', icon: 'success' });
                  setTimeout(() => {
                    wx.reLaunch({ url: '/pages/index/index' });
                  }, 500);
                } else {
                  // 如果微信登录失败，尝试使用测试账号登录
                  this.loginWithTestAccount();
                }
              },
              fail: () => {
                wx.hideLoading();
                // 如果微信登录失败，尝试使用测试账号登录
                this.loginWithTestAccount();
              }
            });
          } else {
            wx.hideLoading();
            // 如果微信登录失败，尝试使用测试账号登录
            this.loginWithTestAccount();
          }
        },
        fail: () => {
          wx.hideLoading();
          // 如果微信登录失败，尝试使用测试账号登录
          this.loginWithTestAccount();
        }
      });
    } else {
      // 用户拒绝授权，尝试使用测试账号登录
      this.loginWithTestAccount();
    }
  },

  // 使用测试账号登录（开发环境使用）
  loginWithTestAccount () {
    wx.showLoading({ title: '使用测试账号登录...' });

    wx.request({
      url: `${app.globalData.baseUrl}/mini/user/account/login`,
      method: 'POST',
      data: {
        username: 'test',
        password: '123456'
      },
      header: { 'content-type': 'application/json' },
      success: res => {
        wx.hideLoading();
        if (res.data.code === 1 && res.data.data) {
          wx.setStorageSync('user', res.data.data.user);
          wx.setStorageSync('token', res.data.data.token);
          wx.showToast({ title: '登录成功(测试账号)', icon: 'success' });
          setTimeout(() => {
            wx.reLaunch({ url: '/pages/index/index' });
          }, 500);
        } else {
          wx.showToast({ title: '登录失败，请重试', icon: 'none' });
        }
      },
      fail: () => {
        wx.hideLoading();
        wx.showToast({ title: '网络错误，请重试', icon: 'none' });
      }
    });
  },

  // 账号密码登录
  onLogin () {
    const { username, password } = this.data;
    if (!username || !password) {
      wx.showToast({ title: '请输入账号和密码', icon: 'none' });
      return;
    }
    wx.showLoading({ title: '登录中...' });
    wx.request({
      url: `${app.globalData.baseUrl}/mini/user/account/login`,
      method: 'POST',
      data: { username, password },
      header: { 'content-type': 'application/json' },
      success: res => {
        wx.hideLoading();
        if (res.data.code === 1 && res.data.data) {
          wx.setStorageSync('user', res.data.data.user);
          wx.setStorageSync('token', res.data.data.token);
          wx.showToast({ title: '登录成功', icon: 'success' });
          setTimeout(() => {
            wx.reLaunch({ url: '/pages/index/index' });
          }, 500);
        } else {
          wx.showToast({ title: res.data.msg || '登录失败', icon: 'none' });
        }
      },
      fail: () => {
        wx.hideLoading();
        wx.showToast({ title: '网络错误', icon: 'none' });
      }
    });
  },

  onInputConfirm (e) {
    this.onLogin();
  }
}); 