import request from '../../utils/request';
import store from '../../utils/store';

const app = getApp();

Page({
  data: {
    activeTab: 'wechat', // 'wechat' 或 'account'
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

  // 切换登录方式
  switchTab (e) {
    const tab = e.currentTarget.dataset.tab;
    this.setData({ activeTab: tab });
  },

  onInputUsername (e) {
    this.setData({ username: e.detail.value });
  },

  onInputPassword (e) {
    this.setData({ password: e.detail.value });
  },

  // 处理登录
  async handleLogin () {
    if (this.data.activeTab === 'wechat') {
      this.handleWechatLogin();
    } else {
      this.handleAccountLogin();
    }
  },

  // 微信登录
  async handleWechatLogin () {
    try {
      // 获取用户信息
      const { code } = await wx.login();

      // 调用后端登录接口
      const res = await request.post('/mini/user/login', { code });

      // 保存登录信息
      wx.setStorageSync('token', res.token);
      store.set('userInfo', res.userInfo);

      // 跳转到首页
      wx.switchTab({
        url: '/pages/index/index'
      });
    } catch (error) {
      wx.showToast({
        title: error.message || '登录失败',
        icon: 'none'
      });
    }
  },

  // 账号密码登录
  async handleAccountLogin () {
    const { username, password } = this.data;

    if (!username || !password) {
      wx.showToast({
        title: '请输入账号和密码',
        icon: 'none'
      });
      return;
    }

    try {
      // 调用后端登录接口
      const res = await request.post('/mini/user/account/login', {
        username,
        password
      });

      // 保存登录信息
      wx.setStorageSync('token', res.token);
      store.set('userInfo', res.userInfo);

      // 跳转到首页
      wx.switchTab({
        url: '/pages/index/index'
      });
    } catch (error) {
      wx.showToast({
        title: error.message || '登录失败',
        icon: 'none'
      });
    }
  },

  onInputConfirm (e) {
    this.handleLogin();
  }
}); 