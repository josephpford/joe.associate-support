function Errors({ errors }) {
  if (errors.length === 0) {
    return null;
  }

  return <>
    <div className="alert alert-danger">
      {errors.map(err => <div key={err}>
        {err}
      </div>)}
    </div>
  </>;
}

export default Errors;