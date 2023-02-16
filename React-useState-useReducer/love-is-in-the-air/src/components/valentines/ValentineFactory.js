import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

import Valentine from "./Valentine";

import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

function ValentineFactory({ messages, setMessages, makeId}) {

    const [valentines, setValentines] = useState([]);
    // const [valentines, setValentines] = [[], (data) => { valentines = data}];

    const navigate = useNavigate();

    useEffect(() => {
        getValentines();
    }, []);

    const getValentines = () => {
        fetch("http://localhost:4000/valentines")
        .then(response => {
            if (response.status == 200) {
                return response.json();
            } else {
                setMessages([...messages, { id: makeId(), type: "failure", text: response.statusText }]);
                navigate("/");
            }
        })
        .then(data => setValentines(data))
        .catch(errors => setMessages([...messages, { id: makeId(), type: "failure", text: errors }]));
    }

    const createValentines = () => {
        return valentines.map(valentine => {
            return (
                <Col className="mb-5" sm={{ span: 4 }}>
                    <Valentine valentine={valentine} />
                </Col>
            )
        });
    }
    // TODO: 
        // Create function to fetch Valentines and save them to state ✔
        // Call Valentine fetch function once on page load ✔
        // Create function to build Valentines from state ✔
        // Call function to display built Valentines ✔

    return (
        <Row className="gx-5">
            {createValentines()}
        </Row>
    );
}

export default ValentineFactory;