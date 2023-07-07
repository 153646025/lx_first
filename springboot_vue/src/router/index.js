import VueRouter from "vue-router";

const routes=[
    {
        path:'/',
        name:'login',
        component:()=>import('../components/LoginLx.vue')
    },
    {
        path: '/IndexLx',
        name: 'IndexLx',
        component:()=>import('../components/IndexLx.vue'),
        children:[
            {
                path: '/HomeLx',
                name: 'home',
                meta:{
                    title:'首页'
                },
                component:()=>import('../components/HomeLx.vue')
            },
            // {
            //     path: '/AdminLx',
            //     name: 'adminlx',
            //     meta:{
            //         title:'管理员管理'
            //     },
            //     component:()=>import('../components/admin/AdminLx.vue')
            // },
            // {
            //     path: '/UserLx',
            //     name: 'userlx',
            //     meta:{
            //         title:'用户管理'
            //     },
            //     component:()=>import('../components/user/UserLx.vue')
            // }
        ]
    }
]

const router = new VueRouter({
    mode:'history',
    routes
})

export function resetRouter() {
    router.matcher = new VueRouter({
        mode:'history',
        routes:[]
    }).matcher
}

const VueRouterPush = VueRouter.prototype.push
VueRouter.prototype.push = function push (to){
    return VueRouterPush.call(this,to).catch(err =>err)
}
export default router;