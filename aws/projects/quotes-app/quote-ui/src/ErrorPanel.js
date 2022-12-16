function ErrorPanel({ errors }) {

    if (!errors || errors.length === 0) {
        return null;
    }

    return (
        <div style={{ backgroundColor: "#f8d7da", padding: "15px 5px 5px 15px" }}>
            <ul>
                {errors.map(err => <li key={err}>{err}</li>)}
            </ul>
        </div>
    )
}

export default ErrorPanel;