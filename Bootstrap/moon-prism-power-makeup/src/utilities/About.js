import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Image from 'react-bootstrap/Image';

function About() {

    return (
        <>
            <Row className="text-center mt-4">
                <p className="h2">This site was lovingly crafted by <span className="bubble-text">Dex Fitch</span> for educational purposes at <span className="bubble-text">Dev10</span><br />...with some outside assistance!</p>
                <Col sm={{ span: 8, offset: 2 }} md={{ span: 6, offset: 3 }} className="mt-3">
                    <Image fluid className="mb-4" src={process.env.PUBLIC_URL + "/images/luna.png"} alt="Luna, a black cat belonging to Sailor Moon" />
                </Col>
                <p className="h4">Sailor Moon was created by Naoko Takeuchi, and is licensed for distribution in the US by VizMedia</p>
            </Row>
        </>
    )
}

export default About;