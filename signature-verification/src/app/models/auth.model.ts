export interface Users{
  "pid": string,
  "firstName":  string,
  "lastName":  string,
  "businessUnit":  string,
  "token": {
    "access_token":  string,
    "token_type":  string,
    "refresh_token":  string,
    "expires_in": number,
    "scope":  string,
    "roles":  string[]
  }
}

export interface Login{
  "username": string,
  "password": string
}

