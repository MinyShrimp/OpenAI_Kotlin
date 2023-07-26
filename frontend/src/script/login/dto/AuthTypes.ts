export interface SignupRequest {
    name: string;
    email: string;
    password: string;
}

export interface LoginRequest {
    email: string;
    password: string;
}

export interface AuthResponse {
    name: string;
    email: string;
    role: string;
    create_at: string;
    update_at: string;
}
