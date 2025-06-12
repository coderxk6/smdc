// 简单的状态管理工具
const store = {
  data: {
    userInfo: null,
    cartItems: [],
    shopInfo: null,
    loading: false
  },
  
  // 监听器
  listeners: [],
  
  // 获取状态
  get(key) {
    return this.data[key];
  },
  
  // 设置状态
  set(key, value) {
    this.data[key] = value;
    this.notify(key);
  },
  
  // 添加监听器
  subscribe(key, callback) {
    this.listeners.push({ key, callback });
  },
  
  // 移除监听器
  unsubscribe(key, callback) {
    this.listeners = this.listeners.filter(
      listener => listener.key !== key || listener.callback !== callback
    );
  },
  
  // 通知监听器
  notify(key) {
    this.listeners
      .filter(listener => listener.key === key)
      .forEach(listener => listener.callback(this.data[key]));
  }
};

export default store; 