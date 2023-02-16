import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Image from 'react-bootstrap/Image';

function About() {

    return (
        <>
            <Row className="text-center mt-4">
                <p className="h2">This site was lovingly crafted by <span className="bubble-text">Dex Fitch</span> for <span className="bubble-text">Dev10</span><br />...with some outside assistance!</p>
                <Col sm={{ span: 8, offset: 2 }} md={{ span: 6, offset: 3 }} className="mt-3">
                    <Image fluid className="mb-4" src={process.env.PUBLIC_URL + "/images/bubble-gum-yellow-star-1.png"} alt="Gold star in a playful, puffy style" />
                </Col>
                <p className="h4">Illustrations by <a href="https://icons8.com/illustrations/author/eEbrZFlkyZbD"><span className="bubble-text">Anna A</span></a> from <a href="https://icons8.com/illustrations"><span className="bubble-text">Ouch!</span></a></p>
                <p className="h4">Title font by <a href="https://fonts.google.com/specimen/Modak/about"><span className="bubble-text">Noopur Datye</span></a> from <a href="https://ektype.in/"><span className="bubble-text">Ek Type</span></a></p>
                <p className="h4">Navigation font by <a href="https://fonts.google.com/specimen/Modak/about"><span className="bubble-text">Pablo Impallari</span></a> from <a href="https://twitter.com/impallaritype"><span className="bubble-text">Impallari Type</span></a></p>
                <p className="h4">Body font by <a href="https://fonts.google.com/specimen/Quicksand/about"><span className="bubble-text">Andrew Paglinawan</span></a></p>
            </Row>
        </>
    )
}

export default About;