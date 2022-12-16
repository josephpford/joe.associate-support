import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { findById, save } from "./api";
import ErrorPanel from "./ErrorPanel";

function QuoteForm() {

    const [quote, setQuote] = useState({
        quoteId: 0,
        phrase: "",
        person: "",
        personImageUrl: ""
    });
    const [errors, setErrors] = useState([]);
    const { quoteId } = useParams();
    const navigate = useNavigate();

    useEffect(() => {
        if (quoteId) {
            findById(quoteId)
                .then(quote => setQuote(quote))
                .catch(setErrors);
        }
    }, [quoteId]);

    const onChange = evt => {
        const clone = { ...quote };
        clone[evt.target.name] = evt.target.value;
        setQuote(clone);
    };

    const onSubmit = evt => {
        evt.preventDefault();
        save(quote)
            .then(cancel)
            .catch(setErrors);
    };

    const cancel = () => navigate("/");

    return (
        <form onSubmit={onSubmit}>
            <fieldset>
                <h1>{quoteId ? "Edit" : "Add"}</h1>
                <label htmlFor="phrase">Phrase</label>
                <textarea id="phrase" name="phrase"
                    value={quote.phrase} onChange={onChange}></textarea>
                <label htmlFor="person">Person</label>
                <input id="person" name="person"
                    value={quote.person} onChange={onChange} />
                <label htmlFor="personImageUrl">Person Image URL</label>
                <input type="url" id="personImageUrl" name="personImageUrl"
                    value={quote.personImageUrl} onChange={onChange} />
                <div style={{ marginTop: "15px" }}>
                    <button type="submit" className="button" style={{ marginRight: "5px" }}>Save</button>
                    <button type="button" className="button button-outline" onClick={cancel}>Cancel</button>
                </div>
            </fieldset>
            <ErrorPanel errors={errors} />
        </form>
    );
}

export default QuoteForm;