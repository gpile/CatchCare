import { vueKeycloak } from '@josempgon/vue-keycloak';
import { App } from "vue";

export default function (app: App) {
    app.use(vueKeycloak, {
        config: {
            url: import.meta.env.VITE_KEYCLOAK_URL as string,
            realm: import.meta.env.VITE_KEYCLOAK_REALM as string,
            clientId: import.meta.env.VITE_KEYCLOAK_CLIENT_ID as string,
        }
    })
}
