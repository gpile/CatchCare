import { toast, type ToastOptions } from 'vue3-toastify'

// Configurazione globale centralizzata
const defaultConfig: ToastOptions = {
    autoClose: 5000,
    theme: 'colored',
    position: 'bottom-right',
    hideProgressBar: false,
    closeButton: true,
    pauseOnHover: true,
    transition: 'bounce',
}

// Toast service tipizzato con configurazione predefinita
export const toastService = {
    success: (msg: string, options?: ToastOptions) =>
        toast.success(msg, { ...defaultConfig, ...options }),
    error: (msg: string, options?: ToastOptions) =>
        toast.error(msg, { ...defaultConfig, ...options }),
    info: (msg: string, options?: ToastOptions) =>
        toast.info(msg, { ...defaultConfig, ...options }),
    warning: (msg: string, options?: ToastOptions) =>
        toast.warning(msg, { ...defaultConfig, ...options }),
    default: (msg: string, options?: ToastOptions) =>
        toast(msg, { ...defaultConfig, ...options }),
}

export default toastService
