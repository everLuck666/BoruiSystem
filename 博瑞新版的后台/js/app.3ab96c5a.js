(function(e){function n(n){for(var r,o,u=n[0],i=n[1],d=n[2],l=0,f=[];l<u.length;l++)o=u[l],Object.prototype.hasOwnProperty.call(a,o)&&a[o]&&f.push(a[o][0]),a[o]=0;for(r in i)Object.prototype.hasOwnProperty.call(i,r)&&(e[r]=i[r]);s&&s(n);while(f.length)f.shift()();return c.push.apply(c,d||[]),t()}function t(){for(var e,n=0;n<c.length;n++){for(var t=c[n],r=!0,o=1;o<t.length;o++){var u=t[o];0!==a[u]&&(r=!1)}r&&(c.splice(n--,1),e=i(i.s=t[0]))}return e}var r={},o={app:0},a={app:0},c=[];function u(e){return i.p+"js/"+({}[e]||e)+"."+{"chunk-115226b8":"aa373c91","chunk-2b30f844":"3a96f776","chunk-496d89d6":"ab60aa56","chunk-575021c5":"41e37384","chunk-399c1c8d":"280fb8db","chunk-446183e9":"91e554cb","chunk-6ae77fde":"aa9358dc","chunk-b05d8bea":"39e2f371","chunk-f41acb46":"01b92abd"}[e]+".js"}function i(n){if(r[n])return r[n].exports;var t=r[n]={i:n,l:!1,exports:{}};return e[n].call(t.exports,t,t.exports,i),t.l=!0,t.exports}i.e=function(e){var n=[],t={"chunk-2b30f844":1,"chunk-496d89d6":1,"chunk-575021c5":1,"chunk-399c1c8d":1,"chunk-446183e9":1,"chunk-6ae77fde":1,"chunk-b05d8bea":1,"chunk-f41acb46":1};o[e]?n.push(o[e]):0!==o[e]&&t[e]&&n.push(o[e]=new Promise((function(n,t){for(var r="css/"+({}[e]||e)+"."+{"chunk-115226b8":"31d6cfe0","chunk-2b30f844":"e79fe902","chunk-496d89d6":"f6c20cac","chunk-575021c5":"88a04f02","chunk-399c1c8d":"75de4657","chunk-446183e9":"e0276488","chunk-6ae77fde":"71ed2695","chunk-b05d8bea":"6f3b09ea","chunk-f41acb46":"d4f37a15"}[e]+".css",a=i.p+r,c=document.getElementsByTagName("link"),u=0;u<c.length;u++){var d=c[u],l=d.getAttribute("data-href")||d.getAttribute("href");if("stylesheet"===d.rel&&(l===r||l===a))return n()}var f=document.getElementsByTagName("style");for(u=0;u<f.length;u++){d=f[u],l=d.getAttribute("data-href");if(l===r||l===a)return n()}var s=document.createElement("link");s.rel="stylesheet",s.type="text/css",s.onload=n,s.onerror=function(n){var r=n&&n.target&&n.target.src||a,c=new Error("Loading CSS chunk "+e+" failed.\n("+r+")");c.code="CSS_CHUNK_LOAD_FAILED",c.request=r,delete o[e],s.parentNode.removeChild(s),t(c)},s.href=a;var h=document.getElementsByTagName("head")[0];h.appendChild(s)})).then((function(){o[e]=0})));var r=a[e];if(0!==r)if(r)n.push(r[2]);else{var c=new Promise((function(n,t){r=a[e]=[n,t]}));n.push(r[2]=c);var d,l=document.createElement("script");l.charset="utf-8",l.timeout=120,i.nc&&l.setAttribute("nonce",i.nc),l.src=u(e);var f=new Error;d=function(n){l.onerror=l.onload=null,clearTimeout(s);var t=a[e];if(0!==t){if(t){var r=n&&("load"===n.type?"missing":n.type),o=n&&n.target&&n.target.src;f.message="Loading chunk "+e+" failed.\n("+r+": "+o+")",f.name="ChunkLoadError",f.type=r,f.request=o,t[1](f)}a[e]=void 0}};var s=setTimeout((function(){d({type:"timeout",target:l})}),12e4);l.onerror=l.onload=d,document.head.appendChild(l)}return Promise.all(n)},i.m=e,i.c=r,i.d=function(e,n,t){i.o(e,n)||Object.defineProperty(e,n,{enumerable:!0,get:t})},i.r=function(e){"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},i.t=function(e,n){if(1&n&&(e=i(e)),8&n)return e;if(4&n&&"object"===typeof e&&e&&e.__esModule)return e;var t=Object.create(null);if(i.r(t),Object.defineProperty(t,"default",{enumerable:!0,value:e}),2&n&&"string"!=typeof e)for(var r in e)i.d(t,r,function(n){return e[n]}.bind(null,r));return t},i.n=function(e){var n=e&&e.__esModule?function(){return e["default"]}:function(){return e};return i.d(n,"a",n),n},i.o=function(e,n){return Object.prototype.hasOwnProperty.call(e,n)},i.p="",i.oe=function(e){throw console.error(e),e};var d=window["webpackJsonp"]=window["webpackJsonp"]||[],l=d.push.bind(d);d.push=n,d=d.slice();for(var f=0;f<d.length;f++)n(d[f]);var s=l;c.push([0,"chunk-vendors"]),t()})({0:function(e,n,t){e.exports=t("56d7")},"034f":function(e,n,t){"use strict";t("85ec")},"56d7":function(e,n,t){"use strict";t.r(n);t("d3b7"),t("e260"),t("e6cf"),t("cca6"),t("a79d");var r=t("2b0e"),o=function(){var e=this,n=e.$createElement,t=e._self._c||n;return t("div",{attrs:{id:"app"}},[t("router-view")],1)},a=[],c=(t("034f"),t("2877")),u={},i=Object(c["a"])(u,o,a,!1,null,null,null),d=i.exports,l=t("a18c"),f=t("2f62");r["default"].use(f["a"]);var s=new f["a"].Store({state:{},mutations:{},actions:{},modules:{}}),h=t("5c96"),p=t.n(h),m=(t("0fae"),t("bc3a")),b=t.n(m),g=t("313e");r["default"].prototype.$echarts=g["default"],r["default"].config.productionTip=!1,r["default"].use(p.a),r["default"].prototype.$axios=b.a,b.a.defaults.baseURL="https://acfly.cn/background",new r["default"]({router:l["a"],store:s,render:function(e){return e(d)}}).$mount("#app"),b.a.interceptors.request.use((function(e){return localStorage.token&&(e.headers.token=localStorage.token),e}),(function(e){return Promise.reject(e)})),b.a.interceptors.response.use((function(e){var n=e.data,t=n.state;return!1===t&&(location.href="#/login"),n}),(function(e){return Promise.reject(e)}))},"85ec":function(e,n,t){},a18c:function(e,n,t){"use strict";t("d3b7");var r=t("2b0e"),o=t("8c4f");r["default"].use(o["a"]);var a=[{path:"/",redirect:"/index/statistics"},{path:"/login",name:"Login",component:function(){return t.e("chunk-6ae77fde").then(t.bind(null,"dc3f"))},meta:{needLogin:!1}},{path:"/index",name:"Index",component:function(){return t.e("chunk-399c1c8d").then(t.bind(null,"1e4b"))},meta:{needLogin:!0},children:[{path:"statistics",name:"Statistics",component:function(){return t.e("chunk-f41acb46").then(t.bind(null,"d2b3"))},meta:{needLogin:!0}},{path:"order",name:"Order",component:function(){return Promise.all([t.e("chunk-115226b8"),t.e("chunk-575021c5")]).then(t.bind(null,"7915"))},meta:{needLogin:!0}},{path:"resource",name:"Resource",component:function(){return t.e("chunk-b05d8bea").then(t.bind(null,"a116"))},meta:{needLogin:!0}},{path:"commodity",name:"Commodity",component:function(){return Promise.all([t.e("chunk-115226b8"),t.e("chunk-496d89d6")]).then(t.bind(null,"74de"))},meta:{needLogin:!0}},{path:"customer",name:"Customer",component:function(){return Promise.all([t.e("chunk-115226b8"),t.e("chunk-2b30f844")]).then(t.bind(null,"eabe"))},meta:{needLogin:!0}},{path:"system",name:"System",component:function(){return t.e("chunk-446183e9").then(t.bind(null,"18d4"))},meta:{needLogin:!0}}]}],c=new o["a"]({routes:a,mode:"hash"});c.beforeEach((function(e,n,t){e.meta.needLogin?localStorage.getItem("token")?t():(t({path:"/login"}),alert("请登录后再访问主页！")):t()})),n["a"]=c}});
//# sourceMappingURL=app.3ab96c5a.js.map