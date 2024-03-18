import { HttpInterceptorFn } from '@angular/common/http';

export const customInterceptor: HttpInterceptorFn = (req, next) => {
  const token = localStorage.getItem("userToken");
  const cloneReq = req.clone({
    setHeaders:{
      Authorization: `Bearer ${token}`
    }
  })
  console.log(cloneReq)
  return next(cloneReq);
};
