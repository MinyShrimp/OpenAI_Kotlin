import { html, LitElement } from "lit";
import { customElement } from "lit/decorators.js";

@customElement("right-container")
export class RightContainer extends LitElement {    
    render() {
        return html`
            <h1> Right Container </h1>
        `;
    }
}