import axiosInstance from './axios-instance'

export const AUTH_TOKEN_KEY = 'auth_token'

function pickTokenFromResponse(data: unknown): string | null {
    if (!data || typeof data !== 'object') return null
    const candidateKeys = ['token', 'accessToken', 'access_token', 'jwt']
    for (const key of candidateKeys) {
        const value = (data as Record<string, unknown>)[key]
        if (typeof value === 'string' && value.length > 0) return value
    }
    return null
}

export function getToken(): string | null {
    return (
        localStorage.getItem(AUTH_TOKEN_KEY) ||
        sessionStorage.getItem(AUTH_TOKEN_KEY) ||
        null
    )
}

export function isAuthenticated(): boolean {
    return !!getToken()
}

export function logout(): void {
    localStorage.removeItem(AUTH_TOKEN_KEY)
    sessionStorage.removeItem(AUTH_TOKEN_KEY)
}

export async function login(
    username: string,
    password: string,
    remember: boolean,
): Promise<void> {
    const response = await axiosInstance.post('/auth/login', {
        username,
        password,
    })

    const token = pickTokenFromResponse(response.data)
    if (!token) throw new Error('Token non trovato nella risposta del server')

    if (remember) localStorage.setItem(AUTH_TOKEN_KEY, token)
    else sessionStorage.setItem(AUTH_TOKEN_KEY, token)
}



