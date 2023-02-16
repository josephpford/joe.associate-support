import Valentine from "./Valentine";

import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

function ValentineFactory({ messages, setMessages, makeId}) {

    // TODO: 
        // Create function to fetch Valentines and save them to state
        // Call Valentine fetch function once on page load
        // Create function to build Valentines from state
        // Call function to display built Valentines

    const testValentine1 = {
        "id": 1,
        "valentineMessage": "🚂 I choo-choo choose you!",
        "valentineSource": "'The Simpsons'",
        "valentineCategory": "Funny"
    }
    const testValentine2 = {
        "id": 2,
        "valentineMessage": "Don't ever think I fell for you, or fell over you. I didn't fall in love, I rose in it.",
        "valentineSource": "Toni Morrison, 'Jazz'",
        "valentineCategory": "Sentimental"
    }
    const testValentine3 = {
        "id": 3,
        "valentineMessage": "Love can change a person the way a parent can change a baby — awkwardly, and often with a great deal of mess.",
        "valentineSource": "Lemony Snicket",
        "valentineCategory": "Cynical"
    }

    return (
        <Row className="gx-5">
            <Col className="mb-5" sm={{ span: 4 }}>
                <Valentine valentine={testValentine1} />
            </Col>
            <Col className="mb-5" sm={{ span: 4 }}>
                <Valentine valentine={testValentine2} />
            </Col>
            <Col className="mb-5" sm={{ span: 4 }}>
                <Valentine valentine={testValentine3} />
            </Col>
        </Row>
    );
}

export default ValentineFactory;