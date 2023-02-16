import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { findAll } from "./api";
import ErrorPanel from "./ErrorPanel";
import Quote from "./Quote";

function QuoteGrid() {

    const [quotes, setQuotes] = useState([]);
    const [errors, setErrors] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        findAll()
            .then(setQuotes)
            .catch(setErrors);
    }, []);

    const add = () => navigate("/quote");

    return (
        <>
            <div style={{ display: "flex" }}>
                <h1>Quotes</h1>
                <div style={{ width: "100%", textAlign: "right", padding: "15px 10px 0px 0px" }}>
                    <button className="button" onClick={add}>Add a Quote</button>
                </div>
            </div>
            <div style={{ display: "flex" }}>
                {quotes.map(quote => <Quote key={quote.quoteId} quote={quote} />)}
            </div>
            <ErrorPanel errors={errors} />
        </>
    )
}

export default QuoteGrid;