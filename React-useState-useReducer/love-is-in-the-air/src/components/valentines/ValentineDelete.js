import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

import Valentine from "./Valentine";

import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Button from 'react-bootstrap/Button';

function ValentineDelete({ messages, setMessages, makeId }) {
    const [valentine, setValentine] = useState({});
    const { valentineId } = useParams();

    const navigate = useNavigate();

    useEffect(() => {
        getValentine();
    }, []);

    const getValentine = () => {
        fetch("http://localhost:4000/valentines/" + valentineId)
        .then(response => {
            if (response.status == 404) {
                setMessages([...messages, { id: makeId(), type: "failure", text: response.statusText }]);
                navigate("/valentines");
            }
            if (response.status == 200) {
                return response.json()
            }
        })
        .then(data => data ? setValentine(data) : null)
        .catch(error => console.error(error));
    }

    const handleDelete = () => {
        fetch("http://localhost:4000/valentines/" + valentineId, {   
            method: "DELETE"
        })
        .then(response => {
            if (response.status == 404) {
                setMessages([...messages, { id: makeId(), type: "failure", text: response.statusText }]);
            }
            if (response.status == 200) {
                setMessages([...messages, { id: makeId(), type: "success", text: "Valentine was successfully deleted." }])
            }
            navigate("/valentines");
        })
        .catch(error => setMessages([...messages, { id: makeId(), type: "failure", text: error.message }]));
    }

    return (
        <div className="text-center">
            <p className="h4 mb-5">Are you certain you want to delete this Valentine?</p>
            <Row>
                <Col sm={{ span: 4, offset: 4 }}>
                    <Valentine valentine={valentine} />
                </Col>
            </Row>
            <div className="text-center mt-5">
                <Button size="sm" variant="primary" onClick={handleDelete}>Confirm</Button>
                <Button size="sm" variant="secondary" className="ms-2" onClick={() => navigate("/valentines")}>Cancel</Button>
            </div>
        </div>
    );
}

export default ValentineDelete;