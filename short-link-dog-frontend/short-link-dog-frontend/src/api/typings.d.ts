declare namespace API {
  type BaseResponseString_ = {
    code?: number
    data?: string
    message?: string
  }

  type createUsingPOSTParams = {
    /** url */
    url?: string
  }

  type visitUsingGETParams = {
    /** shortLink */
    shortLink: string
  }
}
