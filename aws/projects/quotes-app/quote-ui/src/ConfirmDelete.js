import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { findById, deleteById } from "./api";

function ConfirmDelete() {

    const [quote, setQuote] = useState({ phrase: "", person: "" });
    const { quoteId } = useParams();
    const navigate = useNavigate();

    const cancel = () => navigate("/");

    useEffect(() => {
        if (quoteId) {
            findById(quoteId)
                .then(quote => setQuote(quote))
                .catch(cancel);
        }
    }, [quoteId]);

    const handleDelete = () => {
        deleteById(quoteId)
            .finally((cancel));
    };

    return (
        <>
            <h1>Delete Quote?</h1>
            <label>Phrase</label>
            <div>{quote.phrase}</div>
            <label>Person</label>
            <div>{quote.person}</div>
            <div style={{ marginTop: "15px" }}>
                <button type="button" className="button" style={{ marginRight: "5px" }} onClick={handleDelete}>Delete</button>
                <button type="button" className="button button-outline" onClick={cancel}>Cancel</button>
            </div>
        </>
    );
}

export default ConfirmDelete;