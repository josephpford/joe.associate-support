function ErrorList({ errors }) {
  return <>
    {errors.length > 0 &&
      <div id="errors" className="alert alert-danger">
        {errors.map(err => <div key={err}>{err}</div>)}
      </div>
    }
  </>;
}

export default ErrorList;