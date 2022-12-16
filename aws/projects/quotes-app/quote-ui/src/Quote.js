import { useNavigate } from "react-router-dom";

function Quote({ quote }) {

    const navigate = useNavigate();
    const handleDelete = () => navigate(`/delete/${quote.quoteId}`);
    const handleEdit = () => navigate(`/quote/${quote.quoteId}`);

    return (
        <div style={{ width: "33%", padding: "8px" }}>
            {quote.personImageUrl && <img src={quote.personImageUrl} alt={quote.person} />}
            <div>"{quote.phrase}"</div>
            <div>- {quote.person}</div>
            <div style={{ marginTop: "15px" }}>
                <button className="button" style={{ marginRight: "5px" }} onClick={handleDelete}>Delete</button>
                <button className="button button-outline" onClick={handleEdit}>Edit</button>
            </div>
        </div>
    );
}

export default Quote;