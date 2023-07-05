import "../scss/index.scss";
import { CSSResultGroup, LitElement, html, css } from "lit";
import { customElement } from "lit/decorators.js";
import "./components/left_container.ts";
import "./components/right_container.ts";

@customElement("main-container")
export class IndexElement extends LitElement {
    protected createRenderRoot(): Element {
        return this;
    }

    static styles: CSSResultGroup = css`
        :host {
            background: #f5f5f5;
        }
    `;

    protected render() {
        return html`
            <left-container> </left-container>
            <right-container> </right-container>
        `;
    }
}
