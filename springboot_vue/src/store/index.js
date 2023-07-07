import vue from 'vue'
import Vuex from 'vuex'
import router,{resetRouter} from "@/router";

vue.use(Vuex)

function addNewRoute(menulist) {
    console.log(menulist)
    let routes = router.options.routes
    console.log(routes)
    routes.forEach(routeItem=>{
        if (routeItem.path=="/IndexLx"){
            menulist.forEach(menu=>{
                let childRoute = {
                    path: '/'+menu.menuclick,
                    name: menu.menuname,
                    meta:{
                        title:menu.menuname,
                    },
                    component:()=>import('../components/'+menu.menucomponent)
                }
                routeItem.children.push(childRoute)
            })
        }
    })
    resetRouter()
    router.addRoutes(routes)
}

export default new Vuex.Store({
    state: {
        menu:[]

    },
    mutations: {
        setMenu(state,menulist){
            state.menu = menulist;

            addNewRoute(menulist)
        },

    },
    getters: {
        getMenu(state){
            return state.menu
        }
    },

});