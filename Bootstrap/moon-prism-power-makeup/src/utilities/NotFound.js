import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Image from 'react-bootstrap/Image';

function NotFound() {

    const navigate = useNavigate();

    useEffect(()=>{
        setTimeout(()=>{
            navigate("/");
        }, 5000)
    }, [])

    return (
        <>
            <Row className="text-center mt-4">
                <h3>Sorry, the page you requested cannot be found.</h3>
                <Col sm={{ span: 8, offset: 2 }} md={{ span: 6, offset: 3 }} className="mt-3">
                    <Image fluid className="my-5" src={process.env.PUBLIC_URL + "/images/usagi_confused.jpg"} alt="Sailor Moon's titular character 'Usagi', looking confused and surrounded by question marks." />
                </Col>
                <p className="h5">You will be returned to the homepage in 5 seconds...</p>
            </Row>
        </>
    )
}

export default NotFound;